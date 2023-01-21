import { Card } from "react-bootstrap";

const variants = {
  green: "Success",
  yellow: "Warning",
  red: "Danger",
};

export default function AvaibilityCard({ data }) {
  console.log("data: ", data);
  if (!data) {
    return <div>No Data</div>;
  }
  //   const bg = colorDynamic ? variants[data.stockLevel].toLowerCase() : "";
  return (
    <Card>
      <Card.Body>
        {/* <Card bg={variants[data.stockLevel.toLowerCase()].toLowerCase()}> */}
        <Card bg={variants[data.stockLevel.toLowerCase()].toLowerCase()}>
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
