import { Table, Card } from "react-bootstrap";

function MyTable({ tableData }) {
  if (tableData.length === 0) {
    return <div>No Data</div>;
  }

  const columns = Array.from(Object.keys(tableData[0]))
    .map((col) => {
      return col
        .split(/([A-Z][a-z]+)/)
        .filter(Boolean)
        .join(" ");
    })
    .map(
      (newCol) =>
        newCol.substring(0, 1).toUpperCase() +
        newCol.substring(1, newCol.length)
    );
  console.log(columns);
  // console.log(
  //   columns
  //     .map((col) => {
  //       return col
  //         .split(/([A-Z][a-z]+)/)
  //         .filter(Boolean)
  //         .join(" ");
  //     })
  //     .map(
  //       (newCol) =>
  //         newCol.substring(0, 1).toUpperCase() +
  //         newCol.substring(1, newCol.length)
  //     )
  // );

  const rows = tableData.map((item) => Object.values(item));

  return (
    <Card>
      <Card.Header>Table of all resources</Card.Header>
      <Card.Body>
        <Table responsive bordered hover striped>
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
