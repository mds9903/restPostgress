import { useState, useEffect } from "react";
import { Button, Container, Row, Col, Card } from "react-bootstrap";
import axios from "axios";

import MyTable from "../components/MyTable";
// import FormCreateNew from "../components/FormCreateNew";
// import { minHeight } from "@mui/system";

// const getAllUrl = "http://localhost:8088/items/";
const getAllPaginatedUrl = "http://localhost:8088/inventory/items/paginated";

function ItemsPage() {
  const [isDataLoaded, setDataLoaded] = useState(false);
  const [tableData, setTableData] = useState([]);
  const [shouldReload, setShouldReload] = useState(false);
  const [pageSize, setPageSize] = useState(5);
  const [pageNum, setPageNum] = useState(1);
  const [pageNumbers, setPageNumbers] = useState([]);
  // const [formInputStructure, setformInputStructure] = useState();

  useEffect(() => {
    axios
      .get(getAllPaginatedUrl, {
        params: { pageSize: pageSize, pageNum: pageNum },
      })
      .then((response) => {
        console.log("response: ", response);
        // console.log("getting data");
        // console.log("response.data:\n", response.data);
        setDataLoaded(true);
        setTableData(response.data.items);
        // console.log("tableData: ", tableData);
        setPageNumbers(
          Array.from({ length: response.data.maxPages }, (_, i) => i + 1)
        );
        setShouldReload(false);
        // setformInputStructure(
        //   Object.keys(tableData[0]).reduce(
        //     (acc, val) => ({ ...acc, [val]: "" }),
        //     {}
        //   )
        // );
      });
  }, [shouldReload, pageNum]);

  const reloadTable = () => {
    console.log("reloading table");
    console.log("shouldReload" + shouldReload);
    setShouldReload(true);
  };

  // if (!isDataLoaded) {
  //   return <div>No Data</div>;
  // }

  // if (!tableData) return <div>No data</div>;

  return (
    <div>
      <Container>
        {/* heading */}
        <Row className="m-2">
          <Col>
            <h2>Items</h2>
          </Col>
          <Col>
            {/* data reload button */}
            <Button onClick={reloadTable}>Refresh Data</Button>
          </Col>
        </Row>
        {/* heading */}

        {/* views */}
        {isDataLoaded ? (
          tableData ? (
            <Row className="m-2">
              <Col>
                <Col>
                  <Card>
                    <Card.Header>Table of resources</Card.Header>
                    <Card.Body>
                      <Row>
                        <Col>
                          <MyTable tableData={tableData} />
                        </Col>
                        {/* pagination */}
                        <Col
                          className="d-flex flex-column justify-content-between align-items-center "
                          style={{ maxWidth: "50px", maxHeight: "min-content" }}
                        >
                          {pageNumbers.map((item, index) => {
                            return (
                              <Row className="d-flex flex-column justify-content-between align-items-center text-align-center ">
                                <Button
                                  variant="outline-dark"
                                  size="sm"
                                  className="rounded-circle"
                                  key={index}
                                  onClick={() => {
                                    console.log("setting page num to ", item);
                                    setPageNum(item);
                                  }}
                                >
                                  {item}
                                </Button>
                              </Row>
                            );
                          })}
                        </Col>
                      </Row>
                    </Card.Body>
                  </Card>
                </Col>
              </Col>
            </Row>
          ) : (
            <div>No Table Data</div>
          )
        ) : (
          <div>No Data</div>
        )}

        {/* views */}

        {/* actions to create, update resources */}
        {/* <Row className="mt-2"></Row> */}
        {/* forms */}
        {/* <Row className="m-5">
          <Card className="w-auto">
            <Card.Header>Create new Item</Card.Header>
            <Card.Body>
              <FormCreateNew formInputStructure={formInputStructure} />
            </Card.Body>
          </Card>
        </Row> */}
        {/* actions to create, update resources */}
      </Container>
    </div>
  );
}

export default ItemsPage;
