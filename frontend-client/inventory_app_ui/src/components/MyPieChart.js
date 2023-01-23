import { Pie } from "react-chartjs-2";
import { Chart as ChartJS } from "chart.js";
import { Colors } from "chart.js/auto";
import ChartDataLabels from "chartjs-plugin-datalabels";
import { useEffect, useState } from "react";
import { Card, Col, Row } from "react-bootstrap";
import DrillDown from "./DrillDown";

ChartJS.register(Colors);
ChartJS.register(ChartDataLabels);
export default function MyPieChart(props) {
  // const [isDrillDownOpen, setIsDrillDownOpen] = useState(false);
  const [DrillDownData, setDrillDownData] = useState({
    pieVal: null,
    records: null,
  });

  // useEffect(() => {

  // }, [])

  console.log("pie chart data: ", props.data);
  console.log("pie chart data records: ", props.data.records);

  const onClickHandler = (e) => {
    // if (isDrillDownOpen) {
    //   setDrillDownData({
    //     pieVal: e.chart.$context.chart.tooltip.body[0].lines[0],
    //     records: props.data.records,
    //   });
    // } else {
    //   setIsDrillDownOpen(true);
    // }
    console.log(e.chart);
    const modes = e.chart.legend.legendItems.map((item) => item.text);
    const modeSelected = e.chart.$context.chart.tooltip.dataPoints[0].label;
    console.log(modeSelected);
    // console.log(props.data.records[]))
    setDrillDownData({
      pieVal: e.chart.$context.chart.tooltip.body[0].lines[0],
      records: props.data.records,
      modeSelected: modeSelected,
    });
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
    <Row>
      <Col>
        <Card className="m-2">
          <Card.Header>Pie Chart of Different Modes</Card.Header>
          <Card.Body>
            <Pie data={props.data} options={options} />
          </Card.Body>
        </Card>
      </Col>
      <Col>
        <DrillDown data={DrillDownData} />
      </Col>
    </Row>
  );
}
