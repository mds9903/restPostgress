import { Pie } from "react-chartjs-2";
import { Chart as ChartJS } from "chart.js/auto";
import { Colors } from "chart.js/auto";
import ChartDataLabels from "chartjs-plugin-datalabels";

ChartJS.register(Colors);
ChartJS.register(ChartDataLabels);
const options = {
  plugins: {
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
};

export default function MyPieChart(props) {
  // console.log(props.data);
  console.log(props.data);
  return <Pie height={100} data={props.data} options={options} />;
}
