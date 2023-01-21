import { Container, Row, Table } from "react-bootstrap";

function MyTable({ tableData }) {
  console.log(tableData);
  // console.log(Object.keys(tableData[0]));

  if (tableData.length === 0) {
    return <div>No Data</div>;
  }

  const columns = Array.from(Object.keys(tableData[0]));
  const rows = tableData.map((item, index) => Object.values(item));

  console.log("Columns:\n", columns);
  console.log("Rows:\n", rows);

  return (
    <Table size="sm" responsive striped bordered hover>
      <thead>
        <tr>
          {columns.map((column, key) => {
            return <th key={key}>{column}</th>;
          })}
        </tr>
      </thead>
      <tbody>
        {rows.map((item, index) => (
          <tr key={index}>
            {item.map((val, key) => (
              <td key={key}>{val && val.toString()}</td>
            ))}
          </tr>
        ))}
      </tbody>
    </Table>
  );
}

export default MyTable;
