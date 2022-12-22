import { Button, Container, Table, Row, Col } from "react-bootstrap";

function ItemsPage() {
  return (
    <div>
      <h1>ItemsPage</h1>
      <Container>
        <Row>
          <Col>
            <h2>All Items</h2>
          </Col>
          <Col>
            <Button>Refresh Data</Button>
          </Col>
        </Row>
        <Row>
          <Table responsive>
            <thead>
              <tr>
                <th>#</th>
                {Array.from({ length: 12 }).map((_, index) => (
                  <th key={index}>Table heading</th>
                ))}
              </tr>
            </thead>
            <tbody>
              <tr>
                <td>1</td>
                {Array.from({ length: 12 }).map((_, index) => (
                  <td key={index}>Table cell {index}</td>
                ))}
              </tr>
              <tr>
                <td>2</td>
                {Array.from({ length: 12 }).map((_, index) => (
                  <td key={index}>Table cell {index}</td>
                ))}
              </tr>
              <tr>
                <td>3</td>
                {Array.from({ length: 12 }).map((_, index) => (
                  <td key={index}>Table cell {index}</td>
                ))}
              </tr>
            </tbody>
          </Table>
        </Row>
      </Container>
    </div>
  );
}

export default ItemsPage;
