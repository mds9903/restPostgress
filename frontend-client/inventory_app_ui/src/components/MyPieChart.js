import { Pie } from "react-chartjs-2";
import { Chart as ChartJS } from "chart.js";
import { Colors } from "chart.js/auto";
import ChartDataLabels from "chartjs-plugin-datalabels";
import { useEffect, useState } from "react";
import { Card, Col, Row, Button } from "react-bootstrap";
import DrillDown from "./DrillDown";

ChartJS.register(Colors);
ChartJS.register(ChartDataLabels);
export default function MyPieChart(props) {
  const [DrillDownData, setDrillDownData] = useState();
  const [showDrillDown, setShowDrillDown] = useState(false);

  // console.log("pie chart data: ", props.data);
  // console.log("pie chart data records: ", props.data.records);

  const onClickHandler = (e) => {
    console.log(e.chart);
    const modeSelected = e.chart.$context.chart.tooltip.dataPoints[0].label;
    console.log(modeSelected);
    setDrillDownData({
      pieVal: e.chart.$context.chart.tooltip.body[0].lines[0],
      records: props.data.records,
      modeSelected: modeSelected,
    });
    setShowDrillDown(true);
  };

  const options = {
    onClick: onClickHandler,
    maintainAspectRatio: false,
    plugins: {
      datalabels: {
        // This code is used to display data values
        // anchor: "start",
        // align: "start",
        clamp: false,
        color: "rgba(0,0,0, 0.9)",
        // borderColor: "black",
        // borderWidth: 1,
        // borderRadius: 30,
        padding: 3,
        textAlign: "center",
        // backgroundColor: "rgba(255,255,255, 0.25)",
        font: {
          weight: "bolder",
          size: 12,
        },
      },
      legend: {
        align: "center",
        position: "left",
      },
    },

    // cutout: 20,
  };

  if (props.data.labels.length === 0 || props.data.datasets.length === 0) {
    return <div>No Data</div>;
  }

  return (
    <Card>
      <Card.Header>Pie Chart of Different Modes</Card.Header>
      <Card.Body>
        <Row>
          <Col style={{ height: "300px", width: "500px" }}>
            <Pie
              className="scroll"
              // height={200}
              // style={{ overflow: "scroll" }}
              data={props.data}
              options={options}
            />
          </Col>
          {DrillDown === undefined ? (
            <Col
              style={{ width: "500px", height: "300px", overflow: "scroll" }}
            >
              <Card>
                <Card.Header>
                  <Button onClick={() => setShowDrillDown(false)}>x</Button>
                </Card.Header>
                <Card.Body>
                  <div>No data</div>
                </Card.Body>
              </Card>
            </Col>
          ) : showDrillDown ? (
            <Col
              style={{ width: "500px", height: "300px", overflow: "scroll" }}
            >
              <Card>
                <Card.Header>
                  <Button className="" onClick={() => setShowDrillDown(false)}>
                    x
                  </Button>
                </Card.Header>
                <Card.Body>
                  <DrillDown data={DrillDownData} />
                </Card.Body>
              </Card>
            </Col>
          ) : (
            <></>
          )}
          {/* </Col> */}
        </Row>
      </Card.Body>
    </Card>
  );
}
