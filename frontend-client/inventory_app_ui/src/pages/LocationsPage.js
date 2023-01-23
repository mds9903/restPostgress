import { useState, useEffect } from "react";
import MyTable from "../components/MyTable";
import { Button, Container, Row, Col, Card } from "react-bootstrap";

import axios from "axios";
import MyPieChart from "../components/MyPieChart";

const getAllUrl = "http://localhost:8088/inventory/locations/";

const emptyChartData = {
  labels: [],
  datasets: [],
};

function LocationsPage() {
  const [isDataLoaded, setDataLoaded] = useState(false);
  const [tableData, setTableData] = useState(null);
  const [shouldReload, setShouldReload] = useState(false);
  const [chartData, setChartData] = useState(emptyChartData);

  useEffect(() => {
    axios
      .get(getAllUrl)
      .then((response) => {
        // console.log("getting data");
        // console.log("response.data:\n", response.data);
        setDataLoaded(true);
        setTableData(response.data);
        const allModes = {
          mode: "All Modes Allowed",
          data: tableData.filter(
            (location) =>
              location.shippingAllowed === true &&
              location.pickupAllowed === true &&
              location.deliveryAllowed === true
          ),
        };
        const onlyPickup = {
          mode: "Only Pickup Allowed",
          data: tableData.filter(
            (location) =>
              location.shippingAllowed === false &&
              location.pickupAllowed === true &&
              location.deliveryAllowed === false
          ),
        };
        const onlyShipping = {
          mode: "Only Shipping Allowed",
          data: tableData.filter(
            (location) =>
              location.shippingAllowed === true &&
              location.pickupAllowed === false &&
              location.deliveryAllowed === false
          ),
        };
        setChartData(
          response.data.length > 0
            ? {
                labels: [
                  "All Modes Allowed",
                  "Only Pickup Allowed",
                  "Only Shipping Allowed",
                ],
                datasets: [
                  {
                    data: [
                      allModes.data.length,
                      onlyPickup.data.length,
                      onlyShipping.data.length,
                    ],
                    backgroundColor: ["green", "orange", "yellow", "grey"],
                    borderColor: "black",
                    borderWidth: 1,
                    // indexAxis: "y",
                  },
                ],
                records: [allModes, onlyPickup, onlyShipping],
              }
            : emptyChartData
        );
        // console.log(chartData);
        setShouldReload(false);
      })
      .catch((error) => {
        // console.log("Error occurred: ", error);
      });
  }, [shouldReload, isDataLoaded]);

  const reloadTable = () => {
    // console.log("reloading table");
    // console.log("shouldReload" + shouldReload);
    setShouldReload(true);
  };

  return (
    <div>
      <Container>
        <Row className="m-2">
          <Col>
            <h2>Locations</h2>
          </Col>
          <Col>
            {/* data reload button */}
            <Button onClick={reloadTable}>Refresh Data</Button>
          </Col>
        </Row>
        <Row className="m-2">
          <Col>
            {/* chart */}
            {isDataLoaded ? (
              <MyPieChart data={chartData} />
            ) : (
              <div>No Data</div>
            )}
          </Col>
        </Row>
        <Row className="m-2">
          {/* table */}
          <Col>
            {isDataLoaded ? (
              tableData ? (
                <MyTable tableData={tableData} />
              ) : (
                <div>No Table Data</div>
              )
            ) : (
              <div>No Data</div>
            )}
          </Col>
        </Row>
      </Container>
    </div>
  );
}

export default LocationsPage;
