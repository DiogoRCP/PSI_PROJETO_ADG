<?php

use yii\helpers\Html;
use yii\grid\GridView;

/* @var $this yii\web\View */
/* @var $searchModel backend\models\ContributorSearch */
/* @var $dataProvider yii\data\ActiveDataProvider */

$this->title = 'Contributors';
?>
<div class="contributors-index">
    <h1><?= Html::encode($this->title) ?></h1>
    <p>
        <?= Html::a('Add Contributor', ['create'], ['class' => 'btn btn-secondary']) ?>
    </p>

    <?php //echo $this->render('_search', ['model' => $searchModel]); ?>

    <?= GridView::widget([
        'dataProvider' => $dataProvider,
        'filterModel' => $searchModel,
        //Quando um dado for nulo nao mostra o not set
        'formatter' => ['class' => 'yii\i18n\Formatter','nullDisplay' => ''],
        'columns' => [


            [   'label' => 'User',
                'class' => 'yii\grid\DataColumn', // can be omitted, as it is the default
                'value' => function ($data) {
                    return $data->user->username; // $data['name'] for array data, e.g. using SqlDataProvider.
                },
            ],
            [   'label' => 'Company',
                'class' => 'yii\grid\DataColumn', // can be omitted, as it is the default
                'value' => function ($data) {
                    return $data->company->companyname; // $data['name'] for array data, e.g. using SqlDataProvider.
                },
            ],
            'speciality',
            ['class' => 'yii\grid\ActionColumn'],
        ],
    ]); ?>


</div>
