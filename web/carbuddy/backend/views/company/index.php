<?php

use yii\helpers\Html;
use yii\grid\GridView;

/* @var $this yii\web\View */
/* @var $searchModel backend\models\CompanySearch */
/* @var $dataProvider yii\data\ActiveDataProvider */

$this->title = 'Companies';
?>
<div class="companies-index">
    <h1><?= Html::encode($this->title) ?></h1>

    <p>
        <?= Html::a('Add Company', ['create'], ['class' => 'btn btn-secondary']) ?>
    </p>

    <?php // echo $this->render('_search', ['model' => $searchModel]); ?>

    <?= GridView::widget([
        'dataProvider' => $dataProvider,
        'filterModel' => $searchModel,
        'columns' => [

            'companyname',
            'nif',
            'email:email',
            'phonenumber',
            'registrationdate',

            ['class' => 'yii\grid\ActionColumn'],
        ],
    ]); ?>


</div>
