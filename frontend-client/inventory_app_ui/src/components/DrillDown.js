import { Card } from "react-bootstrap";
import MyTable from "./MyTable";

export default function DrillDown({ data }) {
  console.log(data);
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
  return (
    <Card>
      <Card.Header>DrillDowns</Card.Header>
      <Card.Body>
        {data.pieVal === null ? (
          <div>No Pie Val Data</div>
        ) : (
          `${data.pieVal} Locatins where ${data.modeSelected} are available`
        )}
      </Card.Body>
      <Card.Body>
        {drillDownRecords === null ? (
          <div>No DrillDown Data</div>
        ) : (
          // JSON.stringify(drillDownRecords)
          <MyTable
            hideColumns={[
              "deliveryAllowed",
              "shippingAllowed",
              "pickupAllowed",
            ]}
            tableData={drillDownRecords}
          />
        )}
      </Card.Body>
    </Card>
  );
}
