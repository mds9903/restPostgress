import { Card } from "react-bootstrap";
import MyTable from "./MyTable";

export default function DrillDown({ data }) {
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
  return (
    <div>
      <div>
        {data.pieVal === null ? (
          <div>?No Data?</div>
        ) : (
          `${data.pieVal} Locatins where ${data.modeSelected} are available`
        )}
      </div>
      <div>
        {drillDownRecords === null ? (
          <div>No DrillDown Data</div>
        ) : (
          // JSON.stringify(drillDownRecords)
          <MyTable tableData={drillDownRecords} />
        )}
      </div>
    </div>
  );
}
