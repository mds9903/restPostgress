import { Button, Card, Col, Container, Row } from "react-bootstrap";
import MyDoughnutChart from "../components/MyDoughnutChart";
import { useState, useEffect } from "react";
import axios from "axios";

const inventoryUri = "http://localhost:8088/inventory/";

const emptyChartData = {
  labels: [],
  datasets: [],
};

function HomePage() {
  const [isDataLoaded, setDataLoaded] = useState(false);
  const [shouldReload, setShouldReload] = useState(false);
  const [demandsChartData, setDemandsChartData] = useState(emptyChartData);
  const [suppliesChartData, setSuppliesChartData] = useState(emptyChartData);
  const [locationsChartData, setLocationsChartData] = useState(emptyChartData);

  useEffect(() => {
    // demands
    axios.get(inventoryUri + "demand/").then((response) => {
      console.log("getting data");
      console.log("response.data:\n", response.data);
      setDataLoaded(true);
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
    });

    // supplies
    axios.get(inventoryUri + "supply/").then((response) => {
      if (response.data) {
        console.log("getting data");
        console.log("response.data:\n", response.data);
        setDataLoaded(true);
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
      }
    });

    // locations
    axios.get(inventoryUri + "locations/").then((response) => {
      console.log("getting data");
      console.log("response.data:\n", response.data);
      setDataLoaded(true);
      setLocationsChartData({
        labels: [
          "All Modes Allowed",
          "Only Delivery Allowed",
          "Only Pickup Allowed",
          "Only Shipping Allowed",
        ],
        datasets: [
          {
            data: [
              response.data.filter(
                (location) =>
                  location.shippingAllowed === true &&
                  location.pickupAllowed === true &&
                  location.deliveryAllowed === true
              ).length,
              response.data.filter(
                (location) =>
                  location.shippingAllowed === false &&
                  location.pickupAllowed === false &&
                  location.deliveryAllowed === true
              ).length,
              response.data.filter(
                (location) =>
                  location.shippingAllowed === false &&
                  location.pickupAllowed === true &&
                  location.deliveryAllowed === false
              ).length,
              response.data.filter(
                (location) =>
                  location.shippingAllowed === true &&
                  location.pickupAllowed === false &&
                  location.deliveryAllowed === false
              ).length,
            ],
            backgroundColor: ["green", "orange", "yellow", "grey"],
            borderColor: "black",
            borderWidth: 1,
            borderRadius: 3,
            spacing: 5,
          },
        ],
      });
    });

    // availabilities
  }, [shouldReload]);

  const reloadData = () => {
    console.log("reloading table");
    console.log("shouldReload" + shouldReload);
    setShouldReload(true);
  };

  return (
    <div>
      <Container className="h-80">
        <Row>
          <Col>
            <Button onClick={reloadData}>Reload Data</Button>
          </Col>
        </Row>
        <Row className="mt-2 h-50">
          <Col className="w-30">
            <Card>
              <Card.Header>
                Supplies that are in transit, damaged and on hand
              </Card.Header>
              <Card.Body>
                <MyDoughnutChart data={suppliesChartData} />
              </Card.Body>
            </Card>
          </Col>
          <Col className="w-30">
            <Card>
              <Card.Header>Demands that are planned, hard promised</Card.Header>
              <Card.Body>
                {isDataLoaded ? (
                  <MyDoughnutChart data={demandsChartData} />
                ) : (
                  <div>No data</div>
                )}
              </Card.Body>
            </Card>
          </Col>
        </Row>
        <Row className="mt-2">
          <Col>
            <Card>
              <Card.Header>
                Locations pie chart that are only shipping, only delivering,
                only pickup, and all modes.
              </Card.Header>
              <Card.Body>
                {/* 
                for each location id
                a line chart showing the 
                */}
                {/* <MyDoughnutChart data={locationsChartData} /> */}
              </Card.Body>
            </Card>
          </Col>

          <Col>
            <Card>
              <Card.Body>
                Items that are in stock, under stocked and over stocked
              </Card.Body>
            </Card>
          </Col>
        </Row>
      </Container>
    </div>
  );
}

export default HomePage;
