import {
  Button,
  Card,
  Col,
  Container,
  Row,
  Form,
  FloatingLabel,
  FormLabel,
} from "react-bootstrap";
import MyDoughnutChart from "../components/MyDoughnutChart";
import { useState, useEffect } from "react";
import axios from "axios";
import AvailabilityCard from "../components/AvailabilityCard";
import { Line } from "react-chartjs-2";
import MyLineChart from "../components/MyLineChart";

const inventoryUri = "http://localhost:8088/inventory/";

const emptyChartData = {
  labels: [],
  datasets: [],
};

const defaultVersion = "2";
const defaultItemId = "131";
const defaultLocationId = "131";

function HomePage() {
  const [isDataLoaded, setIsDataLoaded] = useState(false);
  const [shouldReload, setShouldReload] = useState(false);
  const [demandsChartData, setDemandsChartData] = useState(emptyChartData);
  const [suppliesChartData, setSuppliesChartData] = useState(emptyChartData);
  const [itemsChartData, setItemsChartData] = useState(emptyChartData);
  // const [locationsChartData, setLocationsChartData] = useState(emptyChartData);
  const [version, setVersion] = useState(defaultVersion);
  const [itemId, setItemId] = useState(defaultItemId);
  const [locationId, setLocationId] = useState(defaultLocationId);
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
              backgroundColor: ["green", "orange"],
              borderColor: "black",
              borderWidth: 1,
              borderRadius: 2,
              spacing: 8,
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
              backgroundColor: ["green", "orange", "red"],
              borderColor: "black",
              borderWidth: 1,
              borderRadius: 2,
              spacing: 8,
              // weight: 10,
            },
          ],
        });
        setShouldReload(false);
      })
      .catch((error) => {
        console.log("Error: ", error.response);
      });

    // availabilities
    axios
      .get(`${inventoryUri}v${version}/availability/${itemId}/${locationId}`)
      .then((response) => {
        console.log("getting data");
        console.log("Avb---response.data:\n", response.data);
        setIsDataLoaded(true);
        setLoadedAvbData(response.data);
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
    <Container fluid style={{ height: "89vh", overflow: "auto" }}>
      <Container
        fluid
        // style={{ backgroundColor: "grey" }}
      >
        {/* heading */}
        <Row className="m-2">
          <Col className="m-2 d-flex flex-direction-row justify-content-between">
            <h2>Dashboard</h2>
            {/* data reload button */}
            <Button onClick={reloadData}>Refresh Data</Button>
          </Col>
        </Row>
        {/* dashboard widgets */}
        <Row className="w-100 h-100" md={1} lg={2} xl={2}>
          {/* avaiabilities */}
          <Col>
            <Card className="h-100">
              <Card.Header>Availabilities</Card.Header>
              <Card.Body>
                <Row>
                  {/* form */}
                  <Col>
                    <Form onSubmit={submitHandler}>
                      <Form.Group className="p-2">
                        {/* item id */}
                        <FloatingLabel
                          controlId="floatingInput_item"
                          label={"Item ID: " + itemId}
                        >
                          <Form.Control
                            style={{ outline: "none", boxShadow: "none" }}
                            type="text"
                            placeholder="the item id"
                            onChange={itemIdChangeHandler}
                          />
                        </FloatingLabel>
                      </Form.Group>
                      <Form.Group className="p-2">
                        {/* locationd id */}
                        <FloatingLabel
                          controlId="floatingInput_location"
                          label={"Location ID: " + locationId}
                        >
                          <Form.Control
                            style={{ outline: "none", boxShadow: "none" }}
                            type="text"
                            placeholder="the location id"
                            onChange={locationIdChangeHandler}
                          />
                        </FloatingLabel>
                      </Form.Group>
                      <Form.Group className="p-2">
                        {/* version */}
                        <FloatingLabel
                          style={{ outline: "none", boxShadow: "none" }}
                          controlId="floatingInput_version"
                          label={"Selected version"}
                        >
                          <Form.Control
                            style={{
                              outline: "none",
                              boxShadow: "none",
                            }}
                            as={"select"}
                            onChange={versionChangeHandler}
                          >
                            <option value="2">V2</option>
                            <option value="3">V3</option>
                            {/* <option value="1">V1</option> */}
                          </Form.Control>
                        </FloatingLabel>
                      </Form.Group>
                    </Form>
                  </Col>
                  {/* availability card */}
                  <Col>
                    {/* <Card> */}
                    {isDataLoaded ? (
                      loadedAvbData ? (
                        <AvailabilityCard
                          data={loadedAvbData}
                          itemId={loadedAvbData.itemId}
                          locationId={loadedAvbData.locationId}
                        />
                      ) : (
                        <div>No Data</div>
                      )
                    ) : (
                      <div>No Data</div>
                    )}
                    {/* </Card> */}
                  </Col>
                </Row>
              </Card.Body>
            </Card>
          </Col>
          {/* supplies and demands */}
          <Col>
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
            <Card className="mt-2">
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
      </Container>
    </Container>
  );
}

export default HomePage;

// // locations
// axios
//   .get(inventoryUri + "locations/")
//   .then((response) => {
//     // console.log("getting data");
//     // console.log("response.data:\n", response.data);
//     setIsDataLoaded(true);
//     // setTableData(response.data);
//     const allModes = {
//       mode: "All Modes Allowed",
//       data: response.data.filter(
//         (location) =>
//           location.shippingAllowed === true &&
//           location.pickupAllowed === true &&
//           location.deliveryAllowed === true
//       ),
//     };
//     const onlyPickup = {
//       mode: "Only Pickup Allowed",
//       data: response.data.filter(
//         (location) =>
//           location.shippingAllowed === false &&
//           location.pickupAllowed === true &&
//           location.deliveryAllowed === false
//       ),
//     };
//     const onlyShipping = {
//       mode: "Only Shipping Allowed",
//       data: response.data.filter(
//         (location) =>
//           location.shippingAllowed === true &&
//           location.pickupAllowed === false &&
//           location.deliveryAllowed === false
//       ),
//     };
//     setLocationsChartData(
//       response.data.length > 0
//         ? {
//             labels: [
//               "All Modes Allowed",
//               "Only Pickup Allowed",
//               "Only Shipping Allowed",
//             ],
//             datasets: [
//               {
//                 data: [
//                   allModes.data.length,
//                   onlyPickup.data.length,
//                   onlyShipping.data.length,
//                 ],
//                 backgroundColor: ["green", "orange", "yellow", "grey"],
//                 borderColor: "black",
//                 borderWidth: 1,
//                 // indexAxis: "y",
//               },
//             ],
//             records: [allModes, onlyPickup, onlyShipping],
//           }
//         : emptyChartData
//     );
//     // console.log(chartData);
//     setShouldReload(false);
//   })
//   .catch((error) => {
//     // console.log("Error occurred: ", error);
//   });
