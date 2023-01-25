import { useState, useEffect } from "react";
import axios from "axios";
import AvaibilityCard from "../components/AvaibilityCard";
import {
  Button,
  Container,
  Row,
  Col,
  Form,
  FloatingLabel,
  Card,
} from "react-bootstrap";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

function AvaibilityPage() {
  const [isDataLoaded, setIsDataLoaded] = useState(false);
  const [version, setVersion] = useState("2");
  const [itemId, setItemId] = useState("131");
  const [locationId, setLocationId] = useState("131");
  const [loadedData, setLoadedData] = useState(null);
  const [shouldReload, setShouldReload] = useState(false);

  const getAllUrl = `http://localhost:8088/inventory/`;

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
    axios
      .get(`${getAllUrl}v${version}/availability/${itemId}/${locationId}`)
      .then((response) => {
        console.log("getting data");
        console.log("response.data:\n", response.data);
        setIsDataLoaded(true);
        setLoadedData(response.data);
        // console.log(tableData);
        setShouldReload(false);
      })
      .catch(function (error) {
        // handle error
        console.log("Error occurred: ", error);
        // window.alert("No availability found");

        // setItemId(131);
        // setLocationId(131);
        toast("Availabilities not found");
      });
  }, [shouldReload, isDataLoaded, version, itemId, locationId]);

  const reloadData = () => {
    console.log("reloading data");
    console.log("shouldReload" + shouldReload);
    setShouldReload(true);
  };

  return (
    <div>
      <Container>
        {/* heading */}
        <Row className="mt-2">
          <Col>
            <h2>Avaibilities</h2>
          </Col>
          <Col>
            <Button onClick={reloadData}>Refresh Data</Button>
          </Col>
        </Row>
        <Row className="mt-2 w-100">
          <Row className="p-2">
            {/* form */}
            <Col>
              <Card>
                <Form className={"m-2"} onSubmit={submitHandler}>
                  {/* item id */}
                  <Form.Group>
                    <FloatingLabel
                      controlId="floatingInput_item"
                      label={"Item ID: " + itemId}
                      className="mb-2"
                    >
                      <Form.Control
                        size="sm"
                        type="text"
                        placeholder="the item id"
                        onChange={itemIdChangeHandler}
                      />
                    </FloatingLabel>
                  </Form.Group>
                  {/* locationd id */}
                  <Form.Group>
                    <FloatingLabel
                      controlId="floatingInput_location"
                      label={"Location ID: " + locationId}
                      className="mb-2"
                    >
                      <Form.Control
                        size="sm"
                        type="text"
                        placeholder="the location id"
                        onChange={locationIdChangeHandler}
                        className="mb-2"
                      />
                    </FloatingLabel>
                  </Form.Group>
                  <Form.Group>
                    <Form.Label>Select Avaibility Version</Form.Label>
                    <Form.Control as={"select"} onChange={versionChangeHandler}>
                      <option value="2" className="mb-2">
                        V2 Avaibility
                      </option>
                      <option value="3" className="mb-2">
                        V3 Avaibility
                      </option>
                      <option value="1" className="mb-2">
                        V1 Avaibility
                      </option>
                    </Form.Control>
                  </Form.Group>
                </Form>
              </Card>
            </Col>
            {/* availability card */}
            <Col>
              <Card>
                {isDataLoaded ? (
                  <AvaibilityCard
                    data={loadedData}
                    itemId={loadedData.itemId}
                    locationId={loadedData.locationId}
                  />
                ) : (
                  <div>No Data</div>
                )}
              </Card>
            </Col>
          </Row>
        </Row>
      </Container>
    </div>
  );
}

export default AvaibilityPage;
