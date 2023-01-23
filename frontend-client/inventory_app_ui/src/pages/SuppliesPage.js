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

  const reloadTable = () => {
    console.log("reloading table");
    console.log("shouldReload" + shouldReload);
    setShouldReload(true);
  };

  return (
    <div>
      <Container>
        <Row className="m-2">
          <Col>
            <h2>Supplies</h2>
          </Col>
          <Col>
            <Button onClick={reloadTable}>Refresh Data</Button>
          </Col>
        </Row>
        <Row className="m-2">
          {/* chart */}
          <Col>
            {isDataLoaded ? (
              <MyBarChart data={chartData} />
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

export default SuppliesPage;
