import { Navbar, Nav, Container } from "react-bootstrap";
import { LinkContainer } from "react-router-bootstrap";

function MainNav() {
  return (
    <header>
      <Navbar bg="light" expand="lg">
        <Container>
          <Navbar.Brand>React-Bootstrap</Navbar.Brand>
          <Navbar.Toggle aria-controls="basic-navbar-nav" />
          <Navbar.Collapse id="basic-navbar-nav">
            <Nav className="me-auto" variant="pills">
              <LinkContainer to="/home">
                <Nav.Link>Home</Nav.Link>
              </LinkContainer>

              <LinkContainer to="/items">
                <Nav.Link>Items</Nav.Link>
              </LinkContainer>

              <LinkContainer to="/locations">
                <Nav.Link>Locations</Nav.Link>
              </LinkContainer>

              <LinkContainer to="/supplies">
                <Nav.Link>Supplies</Nav.Link>
              </LinkContainer>

              <LinkContainer to="/demands">
                <Nav.Link>Demands</Nav.Link>
              </LinkContainer>

              <LinkContainer to="/thresholds">
                <Nav.Link>Thresholds</Nav.Link>
              </LinkContainer>

              <LinkContainer to="/availability">
                <Nav.Link>Availability</Nav.Link>
              </LinkContainer>
            </Nav>
          </Navbar.Collapse>
        </Container>
      </Navbar>
    </header>
  );
}

export default MainNav;
