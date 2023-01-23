import { Table, Card } from "react-bootstrap";

function MyTable({ tableData }) {
  if (tableData.length === 0) {
    return <div>No Data</div>;
  }

  const columns = Array.from(Object.keys(tableData[0]));
  console.log(columns);

  const rows = tableData.map((item) => Object.values(item));

  return (
    <Card>
      <Card.Header>Table of resources</Card.Header>
      <Card.Body>
        <Table size="sm" responsive bordered hover>
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
      </Card.Body>
    </Card>
  );
}

export default MyTable;
