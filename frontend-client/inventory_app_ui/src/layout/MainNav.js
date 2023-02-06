import { Navbar, Nav, Container } from "react-bootstrap";
import { LinkContainer } from "react-router-bootstrap";

function MainNav() {
  return (
    <header>
      <Navbar bg="dark" variant="dark" expand="lg" className="mb-2">
        <Container>
          <Navbar.Brand>Inventory App</Navbar.Brand>
          <Navbar.Toggle aria-controls="basic-navbar-nav" />
          <Navbar.Collapse id="basic-navbar-nav">
            <Nav className="me-auto" variant="pills">
              <LinkContainer to="/home">
                <Nav.Link className="m-1 p-2">Home</Nav.Link>
              </LinkContainer>

              <LinkContainer to="/items">
                <Nav.Link className="m-1 p-2">Items</Nav.Link>
              </LinkContainer>

              <LinkContainer to="/locations">
                <Nav.Link className="m-1 p-2">Locations</Nav.Link>
              </LinkContainer>

              <LinkContainer to="/supplies">
                <Nav.Link className="m-1 p-2">Supplies</Nav.Link>
              </LinkContainer>

              <LinkContainer to="/demands">
                <Nav.Link className="m-1 p-2">Demands</Nav.Link>
              </LinkContainer>

              <LinkContainer to="/thresholds">
                <Nav.Link className="m-1 p-2">Thresholds</Nav.Link>
              </LinkContainer>

              <LinkContainer to="/availability">
                <Nav.Link className="m-1 p-2">Availability</Nav.Link>
              </LinkContainer>
            </Nav>
          </Navbar.Collapse>
        </Container>
      </Navbar>
    </header>
  );
}

export default MainNav;
