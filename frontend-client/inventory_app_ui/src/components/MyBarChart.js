import { Bar } from "react-chartjs-2";
import { Chart as ChartJS } from "chart.js/auto";
import { Colors } from "chart.js/auto";
import ChartDataLabels from "chartjs-plugin-datalabels";

ChartJS.register(Colors);
ChartJS.register(ChartDataLabels);
const options = {
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
      anchor: "inside",
      align: "center",
      color: "black",
    },
  },
};

export default function MyBarChart(props) {
  // console.log(props.data);
  // return <Bar data={props.data} options={options} />;
  console.log(props.data);
  return <Bar height={props.height} data={props.data} options={options} />;
}
