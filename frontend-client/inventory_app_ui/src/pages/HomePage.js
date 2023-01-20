import { Col, Container, Row } from "react-bootstrap";
import myCss from "./HomePage.module.css";

function HomePage() {
  return (
    <div>
      <Container>
        <Row>
          <Col>Supplies that are in transit, damaged and on hand</Col>
          <Col>Demands that are planned, hard promised</Col>
        </Row>
        <Row>
          <Col>
            Locations pie chart that are only shipping, only delivering, only
            pickup, and all modes.
          </Col>
          <Col>Items that are in stock, under stocked and over stocked</Col>
        </Row>
      </Container>
    </div>
  );
}

export default HomePage;
