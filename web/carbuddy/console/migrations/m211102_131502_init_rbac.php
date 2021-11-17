<?php

use yii\db\Migration;

/**
 * Class m211102_131502_init_rbac
 */
class m211102_131502_init_rbac extends Migration
{
    /**
     * {@inheritdoc}
     */
    public function safeUp()
    {
        $auth = Yii::$app->authManager;

        //create users
        $admin = $auth->createRole('admin');
        $auth->add($admin);

        $client = $auth->createRole('client');
        $auth->add($client);

        $collaborator = $auth->createRole('collaborator');
        $auth->add($collaborator);

        //Permissions Back-end
        $crudCompany = $auth->createPermission('backendCrudCompany');
        $crudCompany->description = 'backendCrudCompany';
        $auth->add($crudCompany);

        $crudContributor = $auth->createPermission('backendCrudContributor');
        $crudContributor->description = 'backendCrudContributor';
        $auth->add($crudContributor);

        $crudUser = $auth->createPermission('backendCrudUser');
        $crudUser->description = 'backendCrudUser';
        $auth->add($crudUser);


        //Permissions Front-end
        $crudVehicle = $auth->createPermission('frontendCrudVehicle');
        $crudVehicle->description = 'frontendCrudVehicle';
        $auth->add($crudVehicle);

        $crudRepair = $auth->createPermission('frontendCrudRepair');
        $crudRepair->description = 'frontendCrudRepair';
        $auth->add($crudRepair);

        $CrudSchedulesCollaborator = $auth->createPermission('frontendCrudSchedulesCollaborator');
        $CrudSchedulesCollaborator->description = 'frontendCrudSchedulesCollaborator';
        $auth->add($CrudSchedulesCollaborator);

        $CrudSchedulesClient = $auth->createPermission('frontendCrudSchedulesClient');
        $CrudSchedulesClient->description = 'frontendCrudSchedulesClient';
        $auth->add($CrudSchedulesClient);

        $ReadRepair = $auth->createPermission('frontendReadRepair');
        $ReadRepair->description = 'frontendReadRepair';
        $auth->add($ReadRepair);


        //Atribuições
        $auth->addChild($admin, $crudCompany);
        $auth->addChild($admin, $crudContributor);
        $auth->addChild($admin, $crudUser);
        $auth->addChild($admin, $client);

        $auth->addChild($collaborator, $crudRepair);
        $auth->addChild($collaborator, $CrudSchedulesCollaborator);
        $auth->addChild($collaborator, $client);

        $auth->addChild($client, $CrudSchedulesClient);
        $auth->addChild($client, $crudVehicle);
        $auth->addChild($client, $ReadRepair);


        //assign ID to users
        $auth->assign($client, 3);
        $auth->assign($collaborator, 2);
        $auth->assign($admin, 1);

    }

    /**
     * {@inheritdoc}
     */
    public function safeDown()
    {
        $auth = Yii::$app->authManager;

        $auth->removeAll();
    }

/*
    // Use up()/down() to run migration code without a transaction.
    public function up()
    {

    }

    public function down()
    {

    }
*/
}
