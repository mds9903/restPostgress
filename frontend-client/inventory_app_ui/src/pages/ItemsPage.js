import { useState, useEffect } from "react";
import { Button, Container, Row, Col, Card } from "react-bootstrap";
import axios from "axios";

import MyTable from "../components/MyTable";
import FormCreateNew from "../components/FormCreateNew";

// const getAllUrl = "http://localhost:8088/items/";
const getAllPaginatedUrl = "http://localhost:8088/items/paginated";

function ItemsPage() {
  const [isDataLoaded, setDataLoaded] = useState(false);
  const [tableData, setTableData] = useState(null);

  const [shouldReload, setShouldReload] = useState(false);

  const [pageSize, setPageSize] = useState(2);
  const [pageNum, setPageNum] = useState(3);
  const [pageNumbers, setPageNumbers] = useState([]);

  useEffect(() => {
    axios
      .get(getAllPaginatedUrl, {
        params: { pageSize: pageSize, pageNum: pageNum },
      })
      .then((response) => {
        console.log("getting data");
        console.log("response.data:\n", response.data);
        setDataLoaded(true);
        setTableData(response.data);
        console.log("tableData: ", tableData);
        setPageNumbers(
          Array.from({ length: response.data.maxPages }, (_, i) => i + 1)
        );
        setShouldReload(false);
        // const [pageSize, setPageSize] = useState(2);
        // const [pageNum, setPageNum] = useState(3);
      });
  }, [shouldReload, pageNum]);

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
            <MyTable tableData={tableData.items} />
          </Col>
        </Row>
        <Row>
          {pageNumbers.map((item, index) => {
            return (
              <Col>
                <Button
                  key={index}
                  onClick={() => {
                    console.log("setting page num to ", item);
                    setPageNum(item);
                  }}
                >
                  {item}
                </Button>
              </Col>
            );
          })}
        </Row>
        <Row className="m-5">
          <Card className="w-auto">
            <Card.Header>Create new Item</Card.Header>
            <Card.Body>
              <FormCreateNew
                formInputStructure={Object.keys(tableData.items[0]).reduce(
                  (acc, val) => ({ ...acc, [val]: "" }),
                  {}
                )}
              />
            </Card.Body>
          </Card>
        </Row>
      </Container>
    </div>
  );
}

export default ItemsPage;
