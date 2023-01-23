import { Pie } from "react-chartjs-2";
import { Chart as ChartJS } from "chart.js";
import { Colors } from "chart.js/auto";
import ChartDataLabels from "chartjs-plugin-datalabels";
import { useState } from "react";
import { Card } from "react-bootstrap";
import ChartDataModal from "./ChartDataModal";

ChartJS.register(Colors);
ChartJS.register(ChartDataLabels);
export default function MyPieChart(props) {
  const [showModal, setShowModal] = useState(false);
  const [modalData, setModalData] = useState(null);

  const onClickHandler = (e) => {
    console.log("MyPieChart onClick handler");
    console.log("event: ", e);
    console.log(
      JSON.stringify(e.chart.$context.chart.tooltip.body[0].lines[0])
    );
    setModalData(
      JSON.stringify(e.chart.$context.chart.tooltip.body[0].lines[0])
    );
    showModal ? setShowModal(false) : setShowModal(true);
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
    <Card className="m-2" height={"500px"}>
      <Card className="m-2">
        <Card.Header>
          <Pie data={props.data} options={options} />
        </Card.Header>
        <Card.Footer>
          {showModal ? <ChartDataModal data={modalData} /> : <></>}
        </Card.Footer>
      </Card>
      {/* <Card className="m-2"></Card> */}
    </Card>
  );
}
