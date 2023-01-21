import {
  Button,
  Card,
  Col,
  Container,
  Row,
  Form,
  FloatingLabel,
} from "react-bootstrap";
import MyDoughnutChart from "../components/MyDoughnutChart";
import { useState, useEffect } from "react";
import axios from "axios";
import AvaibilityCard from "../components/AvaibilityCard";

const inventoryUri = "http://localhost:8088/inventory/";

const emptyChartData = {
  labels: [],
  datasets: [],
};

function HomePage() {
  const [isDataLoaded, setIsDataLoaded] = useState(false);
  const [shouldReload, setShouldReload] = useState(false);
  const [demandsChartData, setDemandsChartData] = useState(emptyChartData);
  const [suppliesChartData, setSuppliesChartData] = useState(emptyChartData);
  // const [locationsChartData, setLocationsChartData] = useState(emptyChartData);
  const [version, setVersion] = useState("2");
  const [itemId, setItemId] = useState("1");
  const [locationId, setLocationId] = useState("1");
  const [loadedAvbData, setLoadedAvbData] = useState(null);

  const itemIdChangeHandler = (event) => {
    console.log("itemChange: " + event.target.value);
    setItemId(event.target.value);
  };

  const locationIdChangeHandler = (event) => {
    console.log("itemChange: " + event.target.value);
    setLocationId(event.target.value);
  };

  const versionChangeHandler = (event) => {
    console.log("versionChange: " + event.target.value);
    setVersion(event.target.value);
  };

  const submitHandler = (event) => {
    event.preventDefault();
  };

  useEffect(() => {
    // demands
    axios
      .get(inventoryUri + "demand/")
      .then((response) => {
        console.log("getting data");
        console.log("response.data:\n", response.data);
        setIsDataLoaded(true);
        setDemandsChartData({
          labels: ["Hard Promised Demands", "Planned Demands"],
          datasets: [
            {
              label: "hard promised demands",
              data: [
                response.data
                  .filter((demand) => demand.demandType === "HARD_PROMISED")
                  .map((demand) => demand.demandQty)
                  .reduce((acc, curr) => acc + curr),
                response.data
                  .filter((demand) => demand.demandType === "PLANNED")
                  .map((demand) => demand.demandQty)
                  .reduce((acc, curr) => acc + curr),
              ],
              backgroundColor: ["blue", "yellow"],
              borderColor: "black",
              borderWidth: 1,
              borderRadius: 3,
              spacing: 5,
            },
          ],
        });
        setShouldReload(false);
      })
      .catch((error) => {
        console.log("Error occurred: ", error);
      });

    // supplies
    axios
      .get(inventoryUri + "supply/")
      .then((response) => {
        console.log("getting data");
        console.log("response.data:\n", response.data);
        setIsDataLoaded(true);
        setSuppliesChartData({
          // labels show on the x axis
          labels: ["ONHAND SUPPLIES", "INTRANSIT SUPPLIES", "DAMAGED SUPPLIES"],
          datasets: [
            {
              label: "Supply Quantities",
              data: [
                response.data
                  .filter((supply) => supply.type === "ONHAND")
                  .map((supply) => supply.supplyQty)
                  .reduce((acc, curr) => acc + curr),
                response.data
                  .filter((supply) => supply.type === "INTRANSIT")
                  .map((supply) => supply.supplyQty)
                  .reduce((acc, curr) => acc + curr),
                response.data
                  .filter((supply) => supply.type === "DAMAGED")
                  .map((supply) => supply.supplyQty)
                  .reduce((acc, curr) => acc + curr),
              ],
              backgroundColor: ["blue", "yellow", "red"],
              borderColor: "black",
              borderWidth: 1,
              borderRadius: 3,
              spacing: 5,
            },
          ],
        });
        setShouldReload(false);
      })
      .catch((error) => {
        console.log("Error: ", error.response);
      });

    // locations
    // axios.get(inventoryUri + "locations/").then((response) => {
    //   console.log("getting data");
    //   console.log("response.data:\n", response.data);
    //   setIsDataLoaded(true);
    //   setLocationsChartData({
    //     labels: [
    //       "All Modes Allowed",
    //       "Only Delivery Allowed",
    //       "Only Pickup Allowed",
    //       "Only Shipping Allowed",
    //     ],
    //     datasets: [
    //       {
    //         data: [
    //           response.data.filter(
    //             (location) =>
    //               location.shippingAllowed === true &&
    //               location.pickupAllowed === true &&
    //               location.deliveryAllowed === true
    //           ).length,
    //           response.data.filter(
    //             (location) =>
    //               location.shippingAllowed === false &&
    //               location.pickupAllowed === false &&
    //               location.deliveryAllowed === true
    //           ).length,
    //           response.data.filter(
    //             (location) =>
    //               location.shippingAllowed === false &&
    //               location.pickupAllowed === true &&
    //               location.deliveryAllowed === false
    //           ).length,
    //           response.data.filter(
    //             (location) =>
    //               location.shippingAllowed === true &&
    //               location.pickupAllowed === false &&
    //               location.deliveryAllowed === false
    //           ).length,
    //         ],
    //         backgroundColor: ["green", "orange", "yellow", "grey"],
    //         borderColor: "black",
    //         borderWidth: 1,
    //         borderRadius: 3,
    //         spacing: 5,
    //       },
    //     ],
    //   });
    // });

    // availabilities
    axios
      .get(`${inventoryUri}v${version}/availability/${itemId}/${locationId}`)
      .then((response) => {
        console.log("getting data");
        console.log("response.data:\n", response.data);
        setIsDataLoaded(true);
        setLoadedAvbData(response.data);
        // console.log(tableData);
        setShouldReload(false);
      })
      .catch(function (error) {
        // handle error
        console.log("Error occurred: ", error.response.data.message);
      });
  }, [shouldReload, version, itemId, locationId]);

  const reloadData = () => {
    console.log("reloading table");
    console.log("shouldReload" + shouldReload);
    setShouldReload(true);
  };

  return (
    <div>
      <Container>
        {/* heading */}
        <Row>
          <Col>
            <Button onClick={reloadData}>Reload Data</Button>
          </Col>
        </Row>

        <Row className="mt-2">
          {/* supplies */}
          <Col className="w-30">
            <Card>
              <Card.Header>Supplies</Card.Header>
              <Card.Body>
                {isDataLoaded ? (
                  <MyDoughnutChart data={suppliesChartData} />
                ) : (
                  <div>No Data</div>
                )}
              </Card.Body>
            </Card>
          </Col>
          {/* Demands */}
          <Col className="w-30">
            <Card>
              <Card.Header>Demands </Card.Header>
              <Card.Body>
                {isDataLoaded ? (
                  <MyDoughnutChart data={demandsChartData} />
                ) : (
                  <div>No Data</div>
                )}
              </Card.Body>
            </Card>
          </Col>
        </Row>
        <Row className="mt-2">
          {/* avaiabilities */}
          <Col>
            <Card>
              <Card.Header>Quantities Available To Promise</Card.Header>
              <Row className="p-2">
                {/* form */}
                <Col>
                  <Card>
                    <Form className={"m-2"} onSubmit={submitHandler}>
                      <Form.Group>
                        {/* item id */}
                        <FloatingLabel
                          controlId="floatingInput_item"
                          label={"Item ID: " + itemId}
                          className="mb-1"
                        >
                          <Form.Control
                            type="text"
                            placeholder="the item id"
                            onChange={itemIdChangeHandler}
                          />
                        </FloatingLabel>
                      </Form.Group>

                      {/* locationd id */}
                      <FloatingLabel
                        controlId="floatingInput_location"
                        label={"Location ID: " + locationId}
                        className="mb-1"
                      >
                        <Form.Control
                          type="text"
                          placeholder="the location id"
                          onChange={locationIdChangeHandler}
                          className="mb-1"
                        />
                      </FloatingLabel>
                      <Form.Label>Select Avaibility Version</Form.Label>
                      <Form.Control
                        as={"select"}
                        onChange={versionChangeHandler}
                      >
                        <option value="2" className="mb-1">
                          V2 Avaibility
                        </option>
                        <option value="3" className="mb-1">
                          V3 Avaibility
                        </option>
                      </Form.Control>
                    </Form>
                  </Card>
                </Col>
                {/* availability card */}
                <Col>
                  <Card>
                    {isDataLoaded ? (
                      <AvaibilityCard data={loadedAvbData} />
                    ) : (
                      <div>No Data</div>
                    )}
                  </Card>
                </Col>
              </Row>
            </Card>
          </Col>
        </Row>
      </Container>
    </div>
  );
}

export default HomePage;
