import { useState, useEffect } from "react";
import MyTable from "../components/MyTable";
import { Button, Container, Row, Col, Form } from "react-bootstrap";

import axios from "axios";

const getAllUrl = "http://localhost:8088/items/";
const getAllPaginatedUr = "http://localhost:8088/items/paginated";

function ItemsPage() {
  const [pageSize, setPageSize] = useState(1);
  const [pageNum, setPageNum] = useState(3);
  const [isDataLoaded, setDataLoaded] = useState(false);

  const [tableData, setTableData] = useState(null);

  const [shouldReload, setShouldReload] = useState(false);

  useEffect(() => {
    axios
      .get(getAllPaginatedUr, {
        params: { pageSize: pageSize, pageNum: pageNum },
      })
      .then((response) => {
        console.log("getting data");
        console.log("response.data:\n", response.data);
        setDataLoaded(true);
        setTableData(response.data);
        // console.log(tableData);
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
          <Col>
            <MyTable tableData={tableData} />
            <Form.Select>
              <option>Open this select menu</option>
              <option value="1">One</option>
              <option value="2">Two</option>
              <option value="3">Three</option>
            </Form.Select>
          </Col>
        </Row>
      </Container>
    </div>
  );
}

export default ItemsPage;
