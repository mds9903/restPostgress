import { Col, Container, Row } from "react-bootstrap";
import myCss from "./HomePage.module.css";

function HomePage() {
  return (
    <div>
      <Container>
        <Row>
          <section className={myCss.about_section}>
            <Col>
              <article id="intro_article">
                Tracking availability of various items based on locations, the
                demand, supply and threshold quantities can help in managing
                stock levels more efficiently. <br />
                To provide a tool for this - we present the “Inventory App” a
                backend API that provides different endpoints that make it
                simple to retrieve and update information of Items,
                locations,supplies, demands, thresholds, availability quantities
                and stock levels help make business decisions and calculations
                like Available To Promise (ATP) quicker and
              </article>
            </Col>
          </section>
        </Row>
      </Container>
    </div>
  );
}

export default HomePage;
