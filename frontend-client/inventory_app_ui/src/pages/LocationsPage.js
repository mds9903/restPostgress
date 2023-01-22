import { useState, useEffect } from "react";
import MyTable from "../components/MyTable";
import { Button, Container, Row, Col } from "react-bootstrap";

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
        console.log("getting data");
        console.log("response.data:\n", response.data);
        setDataLoaded(true);
        setTableData(response.data);
        setChartData(
          response.data.length > 0
            ? {
                labels: [
                  "All Modes Allowed",
                  // "Only Delivery Allowed",
                  "Only Pickup Allowed",
                  "Only Shipping Allowed",
                ],
                datasets: [
                  {
                    data: [
                      tableData.filter(
                        (location) =>
                          location.shippingAllowed === true &&
                          location.pickupAllowed === true &&
                          location.deliveryAllowed === true
                      ).length,
                      // tableData.filter(
                      //   (location) =>
                      //     location.shippingAllowed === false &&
                      //     location.pickupAllowed === false &&
                      //     location.deliveryAllowed === true
                      // ).length,
                      tableData.filter(
                        (location) =>
                          location.shippingAllowed === false &&
                          location.pickupAllowed === true &&
                          location.deliveryAllowed === false
                      ).length,
                      tableData.filter(
                        (location) =>
                          location.shippingAllowed === true &&
                          location.pickupAllowed === false &&
                          location.deliveryAllowed === false
                      ).length,
                    ],
                    backgroundColor: ["green", "orange", "yellow", "grey"],
                    borderColor: "black",
                    borderWidth: 1,
                    // indexAxis: "y",
                  },
                ],
              }
            : emptyChartData
        );
        console.log(chartData);
        setShouldReload(false);
      })
      .catch((error) => {
        console.log("Error occurred: ", error);
      });
  }, [shouldReload, isDataLoaded]);

  const reloadTable = () => {
    console.log("reloading table");
    console.log("shouldReload" + shouldReload);
    setShouldReload(true);
  };

  return (
    <div>
      <Container>
        <Row>
          <Col>
            <h2>Locations</h2>
          </Col>
          <Col>
            {/* data reload button */}
            <Button onClick={reloadTable}>Refresh Data</Button>
          </Col>
        </Row>
        <Row>
          {/* chart */}
          {isDataLoaded ? (
            chartData ? (
              <MyPieChart data={chartData} />
            ) : (
              <div>No Chart Data</div>
            )
          ) : (
            <div>No Data</div>
          )}
        </Row>
        <Row>
          {/* table */}
          {isDataLoaded ? (
            tableData ? (
              <MyTable tableData={tableData} />
            ) : (
              <div>No Table Data</div>
            )
          ) : (
            <div>No Data</div>
          )}
        </Row>
      </Container>
    </div>
  );
}

export default LocationsPage;
