import { Bar } from "react-chartjs-2";
import { Chart as ChartJS } from "chart.js/auto";
import { Colors } from "chart.js/auto";
import ChartDataLabels from "chartjs-plugin-datalabels";

const onClickHandler = (e) =>
  console.log(JSON.stringify(e.chart.$context.chart.tooltip.body[0].lines[0]));

ChartJS.register(Colors);
ChartJS.register(ChartDataLabels);
const options = {
  onClick: onClickHandler,
  maintainAspectRatio: false,
  indexAxis: "y",
  scales: {
    y: {
      ticks: {
        beginAtZero: true,
      },
    },
  },
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
        weight: "bold",
        size: 16,
      },
    },
    legend: {
      align: "start",
      position: "right",
    },
  },
};

export default function MyBarChart(props) {
  console.log("props.data: ", props.data);
  if (props.data.labels.length === 0 || props.data.datasets.length === 0) {
    return <div>No Data</div>;
  }
  return <Bar height={100} data={props.data} options={options} />;
}
