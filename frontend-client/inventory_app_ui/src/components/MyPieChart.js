import { Pie } from "react-chartjs-2";
import { Chart as ChartJS } from "chart.js";
import { Colors } from "chart.js/auto";
import ChartDataLabels from "chartjs-plugin-datalabels";

ChartJS.register(Colors);
ChartJS.register(ChartDataLabels);
const onClickHandler = (e) =>
  console.log(JSON.stringify(e.chart.$context.chart.tooltip.body[0].lines[0]));
const options = {
  onClick: onClickHandler,
  maintainAspectRatio: false,
  plugins: {
    // tooltip: {
    //   // Tooltip will only receive click events
    //   events: ["click"],
    // },
    datalabels: {
      // This code is used to display data values
      anchor: "center",
      align: "start",
      color: "black",
      font: {
        size: 20,
      },
    },
  },
  interactions: {
    mode: "point",
  },
};

// console.log(canvasPosition);

export default function MyPieChart(props) {
  console.log("pie chart data: ", props.data);
  console.log(props.data.labels.length);
  console.log(props.data.datasets.length);

  if (props.data.labels.length === 0 || props.data.datasets.length === 0) {
    return <div>No Data</div>;
  }
  return <Pie height={100} data={props.data} options={options} />;
}
