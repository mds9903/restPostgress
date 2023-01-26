import { useState, useEffect } from "react";
import MyTable from "../components/MyTable";
import { Button, Container, Row, Col } from "react-bootstrap";

import axios from "axios";

const getAllUrl = "http://localhost:8088/inventory/atpThresholds/";

function ThresholdsPage() {
  const [isDataLoaded, setDataLoaded] = useState(false);

  const [tableData, setTableData] = useState(null);

  const [shouldReload, setShouldReload] = useState(false);

  useEffect(() => {
    axios.get(getAllUrl).then((response) => {
      console.log("getting data");
      console.log("response.data:\n", response.data);
      setDataLoaded(true);
      setTableData(response.data);
      // console.log(tableData);
      setShouldReload(false);
    });
  }, [shouldReload]);

  const reloadData = () => {
    console.log("reloading table");
    console.log("shouldReload" + shouldReload);
    setShouldReload(true);
  };

  return (
    <Container fluid style={{ height: "89vh", overflow: "auto" }}>
      <Container fluid>
        {/* heading */}
        <Row className="mb-2">
          <Col className="w-100 mb-2 d-flex flex-direction-row justify-content-between">
            <h2>Dashboard</h2>

            {/* data reload button */}
            <Button onClick={reloadData}>Refresh Data</Button>
          </Col>
        </Row>
        <Row className="m-2">
          <Col>
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
          </Col>
        </Row>
      </Container>
    </Container>
  );
}

export default ThresholdsPage;
