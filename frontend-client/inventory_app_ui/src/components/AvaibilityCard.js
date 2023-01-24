import { Card } from "react-bootstrap";

const variants = {
  green: "Success",
  yellow: "Warning",
  red: "Danger",
};

export default function AvaibilityCard({ data }) {
  console.log("data: ", data);

  const knowMoreHandler = (event) => {
    console.log("clicked knowMoreHandler");
    console.log(data);
  };

  if (!data) {
    return <div>No Data</div>;
  }
  //   const bg = colorDynamic ? variants[data.stockLevel].toLowerCase() : "";
  return (
    <Card>
      <Card.Body>
        <Card bg={variants[data.stockLevel.toLowerCase()].toLowerCase()}>
          <Card.Header>Items Available To Promise</Card.Header>
          <Card.Body style={{ fontSize: "2.5em" }}>
            {data.availabilityQty}
          </Card.Body>
        </Card>
      </Card.Body>
      <Card.Footer onClick={knowMoreHandler}>More info</Card.Footer>
    </Card>
  );
}
