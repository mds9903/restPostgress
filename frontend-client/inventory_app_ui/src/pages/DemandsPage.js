import { useState, useEffect } from "react";
import MyTable from "../components/MyTable";
import { Button, Container, Row, Col } from "react-bootstrap";

import axios from "axios";
import MyBarChart from "../components/MyBarChart";
import MyPieChart from "../components/MyPieChart";

const getAllUrl = "http://localhost:8088/inventory/demand/";
const emptyChartData = {
  labels: [],
  datasets: [],
};

function DemandsPage() {
  const [isDataLoaded, setDataLoaded] = useState(false);
  const [tableData, setTableData] = useState(null);
  const [shouldReload, setShouldReload] = useState(false);
  const [chartData, setChartData] = useState(emptyChartData);

  useEffect(() => {
    axios.get(getAllUrl).then((response) => {
      console.log("getting data");
      console.log("response.data:\n", response.data);
      setDataLoaded(true);
      setTableData(response.data);
      // console.log(tableData);
      setChartData(
        response.data.length > 0
          ? {
              labels: ["Total Demands"],
              datasets: [
                {
                  label: "hard promised demands",
                  data: [
                    response.data
                      .filter((demand) => demand.demandType === "HARD_PROMISED")
                      .map((demand) => demand.demandQty)
                      .reduce((acc, curr) => acc + curr),
                  ],
                  backgroundColor: "blue",
                  borderColor: "black",
                  borderWidth: 2,
                },
                {
                  label: "planned demands",
                  data: [
                    response.data
                      .filter((demand) => demand.demandType === "PLANNED")
                      .map((demand) => demand.demandQty)
                      .reduce((acc, curr) => acc + curr),
                  ],
                  backgroundColor: "grey",
                  borderColor: "black",
                  borderWidth: 2,
                },
              ],
            }
          : emptyChartData
      );
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
    <Container fluid style={{ maxHeight: "85vh", overflowY: "scroll" }}>
      <Container fluid>
        <Row className="m-2">
          <Col className="m-1">
            <h2>Demands</h2>
          </Col>
          <Col>
            <Button onClick={reloadTable}>Refresh Data</Button>
          </Col>
        </Row>
        <Row className="m-2">
          {/* chart */}
          <Col>
            {isDataLoaded ? (
              chartData ? (
                <MyBarChart data={chartData} />
              ) : (
                <div>No Chart Data</div>
              )
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
    </Container>
  );
}

export default DemandsPage;
