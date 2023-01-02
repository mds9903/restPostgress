import { Form, Button, Card } from "react-bootstrap";
export default function FormCreateNew(props) {
  console.log(props.fields);

  return (
    <Form>
      {props.fields.map((item, index) => {
        return (
          <Form.Group className="m-3">
            <Form.Label>{item}</Form.Label>
            <Form.Control type="text" placeholder={"enter " + item} />
          </Form.Group>
        );
      })}

      <Button variant="primary" type="submit">
        Create
      </Button>
    </Form>
  );
}
