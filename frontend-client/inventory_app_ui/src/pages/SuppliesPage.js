import { useState, useEffect } from "react";
import MyTable from "../components/MyTable";
import { Button, Container, Row, Col } from "react-bootstrap";
import MyBarChart from "./../components/MyBarChart";

import axios from "axios";
import MyStackChart from "../components/MyStackChart";

const getAllUrl = "http://localhost:8088/inventory/supply/";

function SuppliesPage() {
  const [isDataLoaded, setDataLoaded] = useState(false);
  const [tableData, setTableData] = useState(null);
  const [shouldReload, setShouldReload] = useState(false);
  const [chartData, setChartData] = useState({
    labels: [],
    datasets: [],
  });

  useEffect(() => {
    axios.get(getAllUrl).then((response) => {
      if (response.data) {
        console.log("getting data");
        console.log("response.data:\n", response.data);
        setDataLoaded(true);
        setTableData(response.data);
        setChartData({
          labels: ["ONHAND SUPPLIES", "INTRANSIT SUPPLIES", "DAMAGED SUPPLIES"],
          datasets: [
            {
              label: "Supply Quantities",
              data: [
                response.data
                  .filter((supply) => supply.type === "ONHAND")
                  .map((supply) => supply.supplyQty)
                  .reduce((acc, curr) => acc + curr),
                response.data
                  .filter((supply) => supply.type === "INTRANSIT")
                  .map((supply) => supply.supplyQty)
                  .reduce((acc, curr) => acc + curr),
                response.data
                  .filter((supply) => supply.type === "DAMAGED")
                  .map((supply) => supply.supplyQty)
                  .reduce((acc, curr) => acc + curr),
              ],
              backgroundColor: ["blue", "yellow", "red"],
              borderColor: "black",
              borderWidth: 1,
              // indexAxis: "y",
            },
          ],
        });
        console.log("chartData: ", chartData);
        setShouldReload(false);
      }
    });
  }, [shouldReload]);

  const reloadData = () => {
    console.log("reloading table");
    console.log("shouldReload" + shouldReload);
    setShouldReload(true);
  };

  return (
    <Container fluid style={{ height: "89vh", overflowY: "auto" }}>
      <Container fluid>
        {/* heading */}
        <Row className="mb-2">
          <Col className="w-100 mb-2 d-flex flex-direction-row justify-content-between">
            <h2>Dashboard</h2>

            {/* data reload button */}
            <Button onClick={reloadData}>Refresh Data</Button>
          </Col>
        </Row>
        {/* chart */}
        <Row className="m-2">
          <Col>
            {isDataLoaded ? (
              <MyBarChart data={chartData} />
            ) : (
              <div>No Data</div>
            )}
          </Col>
        </Row>
        {/* table */}
        <Row className="m-2">
          <Col className="m-1">
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
    </Container>
  );
}

export default SuppliesPage;
