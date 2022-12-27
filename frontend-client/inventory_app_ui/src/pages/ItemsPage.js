import { useState, useEffect } from "react";

import { Button, Container, Table, Row, Col } from "react-bootstrap";

import axios from "axios";

const getAllUrl = "http://localhost:8088/items/";

function ItemsPage() {
  const [isDataLoaded, setDataLoaded] = useState(false);

  const [tableData, setTableData] = useState(null);

  const [shouldReload, setShouldReload] = useState(false);

  useEffect(() => {
    axios.get(getAllUrl).then((response) => {
      console.log("getting data");
      console.log("response.data:\n", response.data);
      setDataLoaded(true);
      setTableData(response.data);
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
      <h1>ItemsPage</h1>
      <Container>
        <Row>
          <Col>
            <h2>All Items</h2>
          </Col>
          <Col>
            <Button onClick={reloadTable}>Refresh Data</Button>
          </Col>
        </Row>
        <Row>
          <p>{JSON.stringify(tableData)}</p>
          <Col></Col>
        </Row>
      </Container>
    </div>
  );
}

export default ItemsPage;
