import { Pie } from "react-chartjs-2";
import { Chart as ChartJS } from "chart.js/auto";
import { Colors } from "chart.js/auto";

ChartJS.register(Colors);
const options = {
  maintainAspectRatio: false,
  indexAxis: "y",
};

export default function MyPieChart(props) {
  console.log(props.data);
  // return <Pie data={props.data} options={options} />;
  return <Pie data={props.data} />;
}
