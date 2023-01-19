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
      console.log("getting data");
      console.log("response.data:\n", response.data);
      setDataLoaded(true);
      setTableData(response.data);
      setChartData({
        labels: ["Total Supplies"],
        datasets: [
          {
            label: "on hand supplies",
            data: [
              response.data
                .filter((supply) => supply.type === "ONHAND")
                .map((supply) => supply.supplyQty)
                .reduce((acc, curr) => acc + curr),
            ],
            backgroundColor: "blue",
            borderColor: "black",
            borderWidth: 2,
            indexAxis: "y",
          },
          {
            label: "planned supplys",
            data: [
              response.data
                .filter((supply) => supply.type === "INTRANSIT")
                .map((supply) => supply.supplyQty)
                .reduce((acc, curr) => acc + curr),
            ],
            backgroundColor: "grey",
            borderColor: "black",
            borderWidth: 2,
            indexAxis: "y",
          },
        ],
      });
      console.log("chartData: ", chartData);
      setShouldReload(false);
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
        <Row>
          <Col>
            <h2>All Supplies</h2>
          </Col>
        </Row>
        <Row style={{ width: "75%" }}>
          <MyBarChart data={chartData} />
        </Row>
        <Col>
          <Button onClick={reloadTable}>Refresh Data</Button>
        </Col>
        <Row>
          <Col>
            {isDataLoaded ? (
              tableData ? (
                <MyTable tableData={tableData} />
              ) : (
                <div>No data</div>
              )
            ) : (
              <div>Loading data...please wait</div>
            )}
          </Col>
        </Row>
        <Row>
          <div>stacked chart</div>
          <MyStackChart />
        </Row>
      </Container>
    </div>
  );
}

export default SuppliesPage;
