import { Bar } from "react-chartjs-2";
import { Chart as ChartJS } from "chart.js/auto";
import { Colors } from "chart.js/auto";

ChartJS.register(Colors);
const options = {
  maintainAspectRatio: false,
  indexAxis: "y",
};

export default function MyBarChart(props) {
  console.log(props.data);
  // return <Bar data={props.data} options={options} />;
  return <Bar data={props.data} />;
}
