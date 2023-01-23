import { Card } from "react-bootstrap";
import MyTable from "./MyTable";

export default function DrillDown({ data }) {
  console.log(data);
  console.log(data.records);
  return (
    <div>
      {data.pieVal === null ? <div>No data</div> : data.pieVal}
      {/* {data.records === null ? (
        <MyTable data={data.records} />
      ) : (
        <div>No table data</div>
      )} */}
    </div>
  );
}
