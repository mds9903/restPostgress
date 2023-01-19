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
        labels: ["ONHAND", "INTRANSIT"],
        datasets: [
          {
            // label: ["ONHAND", "INTRANSIT"],
            data: [
              response.data.filter((supply) => supply.type === "ONHAND").length,
              response.data.filter((supply) => supply.type === "INTRANSIT")
                .length,
            ],
            // label: ["onhand", "intransit"],
            backgroundColor: ["red", "yellow"],
            indexAxis: "y",
          },
        ],
      });
      console.log("chartData: ", chartData);
      // console.log("supplies page; tableData: ", tableData);
      // const onhand = response.data.filter(
      //   (supply) => supply.type === "ONHAND"
      // ).length;
      // console.log("Onhand: ", onhand);
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
          <h2>All Supplies</h2>
        </Row>
        <Row>
          <Col>
            <Button onClick={reloadTable}>Refresh Data</Button>
          </Col>
          <Col style={{ height: "200px", width: "300px" }}>
            <MyBarChart data={chartData} />
          </Col>
        </Row>
        <Row>
          <Col>
            <MyTable tableData={tableData} />
          </Col>
        </Row>
      </Container>
    </div>
  );
}

export default SuppliesPage;
