import { Pie } from "react-chartjs-2";
import { Chart } from "chart.js";

export default function MyPieChart(props) {
  return <Pie data={props.data} />;
}
