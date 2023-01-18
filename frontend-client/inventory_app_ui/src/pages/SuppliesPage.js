import { useState, useEffect } from "react";
import MyTable from "../components/MyTable";
import { Button, Container, Row, Col } from "react-bootstrap";
import MyBarChart from "./../components/MyBarChart";

import axios from "axios";

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
        labels: ["ONHAND Supplies", "INTRANSIT Supplies"],
        datasets: [
          {
            label: "ONHAND Supplies",
            data: [
              response.data.filter((supply) => supply.type === "ONHAND").length,
            ],
            backgroundColor: "#9BD0F5",
          },
          {
            label: "INTRANSIT Supplies",
            data: [
              response.data.filter((supply) => supply.type === "INTRANSIT")
                .length,
            ],
            backgroundColor: "#FFB1C1",
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

  if (!isDataLoaded) {
    return <div>Loading data</div>;
  }

  if (!tableData) return <div>No data</div>;

  return (
    <div>
      <h1>SuppliesPage</h1>
      <Container>
        <Row>
          <Col>
            <h2>All Supplies</h2>
          </Col>
          <Col>
            <Button onClick={reloadTable}>Refresh Data</Button>
          </Col>
        </Row>
        <Row>
          <Col>
            <MyTable tableData={tableData} />
          </Col>
        </Row>
        <Row>
          <MyBarChart data={chartData} />
        </Row>
      </Container>
    </div>
  );
}

export default SuppliesPage;
