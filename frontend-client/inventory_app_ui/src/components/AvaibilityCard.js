import { Card } from "react-bootstrap";

const variants = {
  Green: "Success",
  Yellow: "Warning",
  Red: "Danger",
};

export default function AvaibilityCard({ data, colorDynamic }) {
  const bg = colorDynamic ? variants[data.stockLevel].toLowerCase() : "";
  return (
    <Card bg="primary" border="primary" style={{ color: "white" }}>
      <Card.Body>
        <Card bg={bg}>
          <Card.Header>Items Available To Promise</Card.Header>
          {/* <Card.Body>{JSON.stringify(data)}</Card.Body> */}
          <Card.Body style={{ fontSize: "2.5em" }}>
            {data.availabilityQty}
          </Card.Body>
          {/* <Card.Body>{data.stockLevel}</Card.Body> */}
          {/* <Card.Footer>Know more</Card.Footer> */}
        </Card>
      </Card.Body>
    </Card>
  );
}
