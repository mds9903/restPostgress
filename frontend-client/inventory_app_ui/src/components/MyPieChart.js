import { Pie } from "react-chartjs-2";
import { Chart as ChartJS } from "chart.js/auto";
import { Colors } from "chart.js/auto";

ChartJS.register(Colors);
const options = {
  // plugins: {
  //   legend: {
  //     display: false,
  //   },
  // },
  maintainAspectRatio: false,
};

export default function MyPieChart(props) {
  return <Pie data={props.data} options={options} />;
}
