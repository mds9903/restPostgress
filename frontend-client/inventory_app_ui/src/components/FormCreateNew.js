import { useState } from "react";
import { Form, Button, Card } from "react-bootstrap";
export default function FormCreateNew(props) {
  const [formInput, setFormInput] = useState(props.formInputStructure);

  console.log(props.formInputStructure);

  const submitHandler = (event) => {
    event.preventDefault();
    console.log("submit form");
    console.log(event);
    console.log(formInput);
  };

  const onChangeHandler = (event) => {
    console.log(event.target.id);
    const key = event.target.id;
    setFormInput({ ...formInput, key: event.target.value });
  };

  return (
    <Form className={"m-2"} onSubmit={submitHandler}>
      {Object.keys(props.formInputStructure).map((item, index) => {
        return (
          <Form.Group className="m-3">
            <Form.Label>{item}</Form.Label>
            <Form.Control
              id={item}
              key={index}
              onChange={onChangeHandler}
              type="text"
              placeholder={"enter " + item}
            />
          </Form.Group>
        );
      })}
      <Button variant="primary" type="submit">
        Create
      </Button>
    </Form>
  );
}
