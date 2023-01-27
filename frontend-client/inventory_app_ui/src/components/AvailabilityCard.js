import axios from "axios";
import { Button, Card, CloseButton } from "react-bootstrap";
import { useState, useEffect } from "react";

const variants = {
  green: "Success",
  yellow: "Warning",
  red: "Danger",
};

const inventoryUri = "http://localhost:8088/inventory/";

export default function AvailabilityCard({ data, itemId, locationId }) {
  // const [itemId, setItemId] = useState(data.itemId);
  // const [locationId, setLocationId] = useState(data.locationId);
  const [itemData, setItemData] = useState();
  // const [locationData, setLoctationData] = useState();
  const [showMoreData, setShowMoreData] = useState(false);
  const [outline, setOutline] = useState("none");

  useEffect(() => {
    axios.get(inventoryUri + "items/" + itemId).then((response) => {
      console.log("response: ", response);
      setItemData(response.data);
    });
    // axios.get(inventoryUri + "locations/" + locationId).then((response) => {
    //   console.log("response: ", response);
    //   setLoctationData(response.data);
    // });
  }, [showMoreData]);

  const knowMoreHandler = (event) => {
    console.log("clicked knowMoreHandler");
    console.log(data);
    setShowMoreData(true);
  };

  if (!data) {
    return <div>No Data</div>;
  }
  //   const bg = colorDynamic ? variants[data.stockLevel].toLowerCase() : "";
  return (
    <Card className="d-flex flex-direction-column">
      <Card className="m-2">
        <Card.Body>
          <Card bg={variants[data.stockLevel.toLowerCase()].toLowerCase()}>
            <Card.Header>Items Available To Promise</Card.Header>
            <Card.Body>{data.availabilityQty}</Card.Body>
          </Card>
        </Card.Body>
        {/* know more btn */}
        {!showMoreData ? (
          <Card.Footer onClick={knowMoreHandler}>
            <Button>Know more</Button>
          </Card.Footer>
        ) : (
          <></>
        )}
      </Card>
      {showMoreData ? (
        <Card className="m-1">
          <Card.Header className="d-flex flex-direction-row justify-content-between">
            Item Info
            <CloseButton
              style={{ outline: "none", boxShadow: "none" }}
              onClick={() => {
                setShowMoreData(false);
              }}
            />
          </Card.Header>
          {itemData ? (
            <>
              <Card.Body>
                <Card.Text className="m-1">
                  Item Name: {itemData.itemDesc}
                </Card.Text>
                <Card.Text className="m-1">
                  Category: {itemData.category}
                </Card.Text>
                <Card.Text className="m-1">
                  Item Type: {itemData.itemType}
                </Card.Text>
              </Card.Body>
            </>
          ) : (
            <Card.Body>No Item Data</Card.Body>
          )}
        </Card>
      ) : (
        <div></div>
      )}
    </Card>
  );
}
