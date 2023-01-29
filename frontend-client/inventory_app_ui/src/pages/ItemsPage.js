import { useState, useEffect } from "react";
import {
  Button,
  Container,
  Row,
  Col,
  Card,
  ButtonGroup,
} from "react-bootstrap";
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
  const [pageCurrent, setPageCurrent] = useState(1);
  const [pageNumbers, setPageNumbers] = useState([]);

  useEffect(() => {
    axios
      .get(getAllPaginatedUrl, {
        params: { pageSize: pageSize, pageNum: pageCurrent },
      })
      .then((response) => {
        console.log("response: ", response);

        setDataLoaded(true);
        setTableData(response.data.items);
        setPageNumbers(
          Array.from({ length: response.data.maxPages }, (_, i) => i + 1)
        );
        setShouldReload(false);
      });
  }, [shouldReload, pageCurrent, pageSize]);

  const reloadData = () => {
    console.log("reloading data");
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
        {/* views */}
        {isDataLoaded ? (
          tableData ? (
            <Row className="m-2">
              <Col>
                <MyTable tableData={tableData} />

                {/* pagination */}

                <Row>
                  <Col className="m-2">
                    <ButtonGroup size="sm">
                      <Button
                        onClick={() => {
                          setPageCurrent(pageCurrent > 1 ? pageCurrent - 1 : 1);
                        }}
                        size={"sm"}
                        variant={"outline-dark"}
                      >
                        Prev
                      </Button>
                      <Button size={"sm"} variant={"outline-dark"}>
                        {pageCurrent}
                      </Button>
                      <Button
                        onClick={() => {
                          setPageCurrent(pageCurrent + 1);
                        }}
                        size={"sm"}
                        variant={"outline-dark"}
                      >
                        Next
                      </Button>
                    </ButtonGroup>
                  </Col>
                  {/* {pageNumbers.map((item, index) => {
                    return (
                      <Col className="d-flex flex-column justify-content-between align-items-center text-align-center ">
                        <Button
                          variant="outline-dark"
                          size="sm"
                          className="rounded-circle"
                          key={index}
                          onClick={() => {
                            console.log("setting page num to ", item);
                            setPageCurrent(item);
                          }}
                        >
                          {item}
                        </Button>
                      </Col>
                    );
                  })} */}
                </Row>
              </Col>
            </Row>
          ) : (
            <div>No Table Data</div>
          )
        ) : (
          <div>No Data</div>
        )}
      </Container>
    </Container>
  );
}

export default ItemsPage;
