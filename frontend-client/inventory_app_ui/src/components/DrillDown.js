import { useState } from "react";
import { Button, Card, Col, Row } from "react-bootstrap";
import MyTable from "./MyTable";

export default function DrillDown({ data }) {
  // const [showDrillDown, setShowDrillDown] = useState(true);

  if (data === undefined) {
    return <div></div>;
  }
  console.log("drilldown data: ", data);
  // console.log(
  //   data.records.filter(
  //     (record) => record.mode.toString() === data.modeSelected.toString()
  //   )
  // );
  const drillDownRecords = data.records
    ? data.records.filter(
        (record) => record.mode.toString() === data.modeSelected.toString()
      )[0].data
    : null;
  console.log("DrillDown Records: ", drillDownRecords);
  const drillDownRecordsSliced = drillDownRecords.map((record) => {
    return Object.entries(record)
      .slice(0, 3)
      .map((entry) => entry[1]);
  });
  console.log("DrillDown Records Sliced: ", drillDownRecordsSliced);
  return (
    <Card>
      <Card.Header>
        <Row className="d-flex flex-row justify-content-between">
          <Col>
            {data.pieVal === null ? (
              <div>?No Data?</div>
            ) : (
              `${data.pieVal} Locatins where ${data.modeSelected} are available`
            )}
          </Col>
        </Row>
      </Card.Header>
      <Card.Body>
        {drillDownRecordsSliced === null ? (
          <div>No DrillDown Data</div>
        ) : (
          // JSON.stringify(drillDownRecords)
          // <MyTable tableData={drillDownRecordsSliced} />
          drillDownRecordsSliced.map((record) => {
            return (
              <Card
                className="m-1 p-2"
                style={{ height: "min-content", overflow: "y" }}
              >
                <Card.Text bsPrefix={"card-text"}>
                  {record.toString().split(",").join(" | ")}
                </Card.Text>
              </Card>
            );
          })
        )}
      </Card.Body>{" "}
    </Card>
  );
}
