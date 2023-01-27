import { useState, useEffect } from "react";
import { Button, Container, Row, Col, Card } from "react-bootstrap";
import axios from "axios";

import MyTable from "../components/MyTable";
import MyPagination from "../components/MyPaginatin";
// import FormCreateNew from "../components/FormCreateNew";
// import { minHeight } from "@mui/system";

const getAllUrl = "http://localhost:8088/inventory/items/";
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
    axios.get(getAllUrl).then((response) => {
      console.log("response: ", response);
      setTableData(response.data);
      setDataLoaded(true);
      setShouldReload(false);
    });
  }, [shouldReload]);

  const reloadData = () => {
    console.log("reloading table");
    console.log("shouldReload" + shouldReload);
    setShouldReload(true);
  };

  // if (!isDataLoaded) {
  //   return <div>No Data</div>;
  // }

  // if (!tableData) return <div>No data</div>;

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
              </Col>

              {/* pagination */}
              <Col>
                <MyPagination data={tableData} />
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
