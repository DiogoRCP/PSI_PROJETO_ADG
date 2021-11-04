<?php
namespace backend\models;
use dosamigos\chartjs\ChartJs;
use yii\helpers\VarDumper;

class Charts
{
    private $type, $label, $dados, $valores, $legenda, $colors;

    public function __construct($type, $label, $labelAndData, $legenda)
    {
        $this->type = $type;
        $this->label = $label;
        $this->dados = $labelAndData['data'];
        $this->valores = $labelAndData['values'];
        $this->legenda = $legenda;
        $this->colors = ["#38A3A5", "#57CC99", "#80ED99", "#C7F9CC", "#DB5461", "#F1EDEE", "#B4A6AB", "#C8B8DB", "#F9F4F5"];
        shuffle($this->colors);
    }

    /**
     * @return mixed
     */
    public function getLabel()
    {
        return $this->label;
    }

    /**
        Método previsório, neste momento prepara o array tipos de user para criar uma instância desta class
        utilizando a variável que tem os dados distinct e um método que devolve o número de utilizadores com esse tipo
    **/
    public static function LabelAndData($data){
        $LabelAndData = [
            'data' => [],
            'values' =>[]
        ];
        foreach ($data as $item) {
            array_push($LabelAndData['data'], $item['usertype']);
            array_push($LabelAndData['values'], $item->getUserTypes());
        }
        return $LabelAndData;
    }

    /**Desenha o gráfico**/
    public function __toString()
    {
        return ChartJs::widget([
            'type' => $this->type,
            'options' => [
                'height' => 400,
                'width' => 400,
            ],
            'data' => [
                'labels' => $this->dados,
                'datasets' => [
                    [
                        'label' => $this->label,
                        'backgroundColor' => $this->colors,
                        'borderColor' => "white",
                        'pointBackgroundColor' => $this->colors,
                        'pointBorderColor' => "#fff",
                        'pointHoverBackgroundColor' => "#fff",
                        'pointHoverBorderColor' => "rgba(179,181,198,1)",
                        'data' => $this->valores
                    ],
                ]
            ],
            'clientOptions' => [
                'legend' => [
                    'display' => $this->legenda,
                    'position' => 'bottom',
                    'labels' => [
                        'fontSize' => 14,
                        'fontColor' => "white",
                    ]
                ]
            ]
        ]);
    }
}

?>