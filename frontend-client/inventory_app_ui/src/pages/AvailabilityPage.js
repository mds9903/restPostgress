import { useState, useEffect } from "react";
import MyTable from "../components/MyTable";
import { Button, Container, Row, Col } from "react-bootstrap";

import axios from "axios";
import { useLoaderData } from "react-router-dom";

// const getAllUrlV2 = "http://localhost:8088/inventory/v2/availability";
// const getAllUrlV3 = "http://localhost:8088/inventory/v3/availability";

function AvaibilityPage() {
  const [isDataLoaded, setIsDataLoaded] = useState(false);
  const [version, setVersion] = useState("1");
  const [loadedData, setLoadedData] = useState(null);

  const [shouldReload, setShouldReload] = useState(false);

  const getAllUrl = `http://localhost:8088/inventory/v${version}/availability`;

  useEffect(() => {
    axios.get(getAllUrl + "/1/1").then((response) => {
      console.log("getting data");
      console.log("response.data:\n", response.data);
      setIsDataLoaded(true);
      setLoadedData(response.data);
      // console.log(tableData);
      setShouldReload(false);
    });
  }, [shouldReload, isDataLoaded, version]);

  const reloadData = () => {
    console.log("reloading data");
    console.log("shouldReload" + shouldReload);
    setShouldReload(true);
  };

  return (
    <div>
      <h1>AvaibilityPage</h1>
      <Container>
        <Row>
          <Col>
            <h2>Avaibilities</h2>
          </Col>
          <Col>
            <Button onClick={reloadData}>Refresh Data</Button>
          </Col>
          <Row>
            <Col>
              <Button
                className="m-2"
                onClick={() => {
                  setVersion("1");
                }}
              >
                {" "}
                V1
              </Button>
              <Button
                className="m-2"
                onClick={() => {
                  setVersion("2");
                }}
              >
                {" "}
                V2
              </Button>
              <Button
                className="m-2"
                onClick={() => {
                  setVersion("3");
                }}
              >
                {" "}
                V3
              </Button>
            </Col>
          </Row>
        </Row>
        <Row>
          {isDataLoaded ? (
            <div>{JSON.stringify(loadedData)}</div>
          ) : (
            <div>Loading data</div>
          )}
        </Row>
      </Container>
    </div>
  );
}

export default AvaibilityPage;
