import { Doughnut } from "react-chartjs-2";
import { Chart as ChartJS } from "chart.js";
import { Colors } from "chart.js/auto";
import ChartDataLabels from "chartjs-plugin-datalabels";

const onClickHandler = (e) =>
  console.log(JSON.stringify(e.chart.$context.chart.tooltip.body[0].lines[0]));

ChartJS.register(Colors);
ChartJS.register(ChartDataLabels);
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

export default function MyDoughnutChart(props) {
  // console.log(props.data);
  console.log(props.data);
  return <Doughnut height={145} data={props.data} options={options} />;
}
