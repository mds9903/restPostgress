import { Pie } from "react-chartjs-2";
import { Chart as ChartJS } from "chart.js";
import { Colors } from "chart.js/auto";
import ChartDataLabels from "chartjs-plugin-datalabels";
import { useEffect, useState } from "react";
import { Card, Col, Row } from "react-bootstrap";
import ChartDataModal from "./ChartDataModal";

ChartJS.register(Colors);
ChartJS.register(ChartDataLabels);
export default function MyPieChart(props) {
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [modalData, setModalData] = useState();

  // useEffect(() => {

  // }, [])

  const onClickHandler = (e) => {
    if (isModalOpen) {
      setModalData(e.chart.$context.chart.tooltip.body[0].lines[0]);
    } else {
      setIsModalOpen(true);
    }
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

  // console.log(canvasPosition);

  console.log("pie chart data: ", props.data);
  console.log(props.data.labels.length);
  console.log(props.data.datasets.length);

  if (props.data.labels.length === 0 || props.data.datasets.length === 0) {
    return <div>No Data</div>;
  }

  return (
    <Row>
      <Col>
        <Card className="m-2">
          <Pie data={props.data} options={options} />
        </Card>
      </Col>
      <Col>
        <Card>{isModalOpen ? <ChartDataModal data={modalData} /> : <></>}</Card>
      </Col>
    </Row>
  );
}
