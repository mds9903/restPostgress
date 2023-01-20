import { Doughnut } from "react-chartjs-2";
import { Chart as ChartJS } from "chart.js";
import { Colors } from "chart.js/auto";
import ChartDataLabels from "chartjs-plugin-datalabels";

ChartJS.register(Colors);
ChartJS.register(ChartDataLabels);
const options = {
  maintainAspectRatio: false,
  plugins: {
    datalabels: {
      // This code is used to display data values
      anchor: "center",
      align: "center",
      color: "black",
      font: {
        size: 15,
      },
    },
  },
};

export default function MyDoughnutChart(props) {
  // console.log(props.data);
  console.log(props.data);
  return <Doughnut height={200} data={props.data} options={options} />;
}