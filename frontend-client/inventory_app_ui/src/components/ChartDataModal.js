import { Card } from "react-bootstrap";

export default function ChartDataModal({ data }) {
  return (
    <div>
      <Card variant={"primary"}>
        <Card.Header>Modal Data</Card.Header>
        <Card.Body>{data}</Card.Body>
      </Card>
    </div>
  );
}
